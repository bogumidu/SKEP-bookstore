package com.bsd.skep.service;

import com.bsd.skep.entity.Book;
import com.bsd.skep.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.queryparser.flexible.standard.config.PointsConfig;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.search.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log
public class LuceneServiceImpl implements LuceneService {

    private final BookRepository bookRepository;
    private final IndexWriter indexWriter;
    private final Analyzer analyzer;
    private final SearcherManager searcherManager;


    @Override
    public void createIndex() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        try {
            createBookIndex(books);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error while creating post index", e);
        }
    }

    @Override
    public List<Book> searchBook(String query) throws QueryNodeException {
        try {
            searcherManager.maybeRefresh();
            IndexSearcher acquire = searcherManager.acquire();
            StandardQueryParser parser = new StandardQueryParser(analyzer);
            parser.setDefaultOperator(StandardQueryConfigHandler.Operator.OR);
            Map<String, PointsConfig> map = new HashMap<>();
            map.put("price", new PointsConfig(new DecimalFormat(), Integer.class));
            parser.setPointsConfigMap(map);
            Query parse = parser.parse(query, "title");
            TopDocs search = acquire.search(new BooleanQuery.Builder()
                            .add(parse, BooleanClause.Occur.MUST)
                            .add(new TermQuery(new Term("type", "book")), BooleanClause.Occur.MUST)
                            .build(),
                    10);
            List<UUID> ids = Arrays.stream(search.scoreDocs).map(scoreDoc -> {
                        try {
                            return acquire.doc(scoreDoc.doc);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(flatDocument -> UUID.fromString(flatDocument.get("id"))).collect(Collectors.toList());
            return (List<Book>) bookRepository.findAllById(ids);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createBook(Book book) throws IOException {
        Document doc = toDocument(book);
        indexWriter.addDocument(doc);
        indexWriter.commit();
    }

    @Override
    public void updateBook(Book book) throws IOException {
        Document doc = toDocument(book);

        indexWriter.updateDocument(new Term("id", book.getId().toString()), doc);
        indexWriter.commit();
    }

    private void createBookIndex(List<Book> all) throws IOException {
        List<Document> docs = new ArrayList<>(all.size());
        for (Book book : all) {
            Document doc = toDocument(book);
            docs.add(doc);
        }
        indexWriter.addDocuments(docs);
    }

    private Document toDocument(Book book) {
        Document doc = new Document();
        doc.add(new StringField("type", "book", Field.Store.YES));
        doc.add(new StringField("id", book.getId().toString(), Field.Store.YES));
        doc.add(new StringField("title", book.getTitle().toLowerCase(Locale.ROOT), Field.Store.YES));
        doc.add(new StringField("author", (book.getAuthor().getLastName()).toLowerCase(Locale.ROOT), Field.Store.YES));
        if (book.getGenre() != null) {
            doc.add(new StringField("genre", book.getGenre().toLowerCase(Locale.ROOT), Field.Store.YES));
        }
        doc.add(new IntPoint("price", book.getPrice()));
        return doc;
    }
}

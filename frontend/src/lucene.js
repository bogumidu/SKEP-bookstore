export default class Lucene {
    static createQueryAll(query) {
        return query + '~4 OR author:' + query + '~4 OR ' + query + '* OR author:' + query + '*';
    }
    static createQueryGenre(query) {
        return 'genre:' + query;
    }
}
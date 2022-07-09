<template>
  <Wrapper ref="wrapper">
    <h1>
      Orders
    </h1>
    <v-data-table :headers="headersOrder" :items="orders" :items-per-page="10" class="elevation-1">
      <template v-slot:item.creationDate="{ item }">
        <span>{{ new Date(item.creationDate).toLocaleString() }}</span>
      </template>
      <template v-slot:item.total="{ item }">
        <span>{{ (item.total / 100).toFixed(2) + ' PLN' }}</span>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-select :items="ordersActions" v-model="item.status" :label="item.status"
                  @change="updateOrder(item.id, item.status)"></v-select>
      </template>
    </v-data-table>
    <h1>
      Books
    </h1>
    <v-data-table :headers="headersBooks" :items="books" :items-per-page="10" class="elevation-1">
      <template v-slot:item.price="{ item }">
        <span>{{ (item.price / 100).toFixed(2) + ' PLN' }}</span>
      </template>
      <template v-slot:item.author="{ item }">
        <span>{{ item.author.firstName + ' ' + item.author.lastName }}</span>
      </template>
      <template v-slot:top>
        <v-toolbar flat>
          <v-dialog v-model="createBookDialog" width="500">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="secondary" v-bind="attrs" v-on="on">
                <v-icon>mdi-plus</v-icon>
                Create Book
              </v-btn>
            </template>
            <v-card>
              <v-card-title class="text-h5 lighten-2">
                Create Book
                <v-spacer></v-spacer>
                <v-btn right text @click="createBookDialog = false">
                  <v-icon text>mdi-window-close</v-icon>
                </v-btn>
              </v-card-title>
              <v-card-text>
                <v-text-field type="text" v-model="bookTitle" placeholder="Book title"></v-text-field>
              </v-card-text>
              <v-card-text>
                <v-text-field type="text" v-model="bookDescription" placeholder="Book description"></v-text-field>
              </v-card-text>
              <v-card-text>
                <v-text-field type="text" v-model="bookGenre" placeholder="Book genre"></v-text-field>
              </v-card-text>
              <v-card-text>
                <v-text-field type="text" v-model="bookPrice" placeholder="Book price"></v-text-field>
              </v-card-text>
              <v-card-text>
                <v-select v-model="bookAuthor" :items="bookAuthors" placeholder="Author"></v-select>
              </v-card-text>
              <v-divider></v-divider>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="secondary" @click="createBook">
                  Create
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <!--      <template v-slot:item.actions="{ item }">-->
      <!--      </template>-->
    </v-data-table>
    <h1>
      Authors
    </h1>
    <v-data-table :headers="headersAuthors" :items="authors" :items-per-page="10" class="elevation-1">
      <template v-slot:item.firstName="{ item }">
        <span>{{ item.firstName + ' ' + item.lastName }}</span>
      </template>
      <template v-slot:top>
        <v-toolbar flat>
          <v-dialog v-model="createAuthorDialog" width="500">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="secondary" v-bind="attrs" v-on="on">
                <v-icon>mdi-plus</v-icon>
                Create Author
              </v-btn>
            </template>
            <v-card>
              <v-card-title class="text-h5 lighten-2">
                Create Author
                <v-spacer></v-spacer>
                <v-btn right text @click="createAuthorDialog = false">
                  <v-icon text>mdi-window-close</v-icon>
                </v-btn>
              </v-card-title>
              <v-card-text>
                <v-text-field type="text" v-model="authorFirstname" placeholder="Firstname"></v-text-field>
              </v-card-text>
              <v-card-text>
                <v-text-field type="text" v-model="authorLastname" placeholder="Lastname"></v-text-field>
              </v-card-text>
              <v-divider></v-divider>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="secondary" @click="createAuthor">
                  Create
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <!--      <template v-slot:item.actions="{ item }">-->
      <!--      </template>-->
    </v-data-table>
  </Wrapper>
</template>

<script>
import Wrapper from "./Wrapper";
import api from "../api";

export default {
  name: "HomePage",
  components: {Wrapper},
  data: function () {
    return {
      createBookDialog: false,
      bookTitle: '',
      bookDescription: '',
      bookGenre: '',
      bookPrice: '',
      bookAuthor: '',
      createAuthorDialog: false,
      authorFirstname: '',
      authorLastname: '',
      headersOrder: [
        {
          text: 'Order status',
          align: 'start',
          value: 'orderStatus',
          sortable: false
        },
        {
          text: 'Order date',
          align: 'start',
          value: 'creationDate',
          sortable: true
        },
        {
          text: 'Order total',
          align: 'start',
          value: 'total',
          sortable: false
        },
        {
          text: 'Actions',
          align: 'center',
          value: 'actions',
          sortable: false
        }
      ],
      headersBooks: [
        {
          text: 'Title',
          align: 'start',
          value: 'title',
          sortable: true
        },
        {
          text: 'Author',
          align: 'start',
          value: 'author',
          sortable: true
        },
        {
          text: 'Price',
          align: 'start',
          value: 'price',
          sortable: true
        },
        {
          text: 'Actions',
          align: 'center',
          value: 'actions',
          sortable: false
        }
      ],
      headersAuthors: [
        {
          text: 'Name',
          align: 'start',
          value: 'firstName',
          sortable: true
        },
        {
          text: 'ID',
          align: 'center',
          value: 'id',
          sortable: false
        }
      ],
      ordersActions: [
        'PENDING',
        'PAID',
        'SHIPPED',
        'DELIVERED',
        'CANCELED'
      ],
      orders: [],
      books: [],
      authors: [],
      bookAuthors: []
    };
  },
  mounted() {
    api.getAllOrders().then(response => {
      this.orders = response.data.data.orders;
    }).then(() => {
      api.getAllBooks().then(response => {
        this.books = response.data.data.books;
      }).then(() => {
        api.getAuthors().then(response => {
          let authors = response.data.data.authors;
          this.authors = authors;
          this.bookAuthors = authors.map(author => author.firstName + ' ' + author.lastName);
        });
      });
    });
  },
  methods: {
    updateOrder(id, status) {
      api.updateOrder(id, status)
    },
    createBook() {
      let bookAuthorId = this.authors.find(author => author.firstName === this.bookAuthor).id;
      api.createBook(this.bookTitle, this.bookDescription, this.bookGenre, this.bookPrice, bookAuthorId)
          .then(response => {
            this.books.push(response.data.data);
            this.bookTitle = '';
            this.bookDescription = '';
            this.bookGenre = '';
            this.bookPrice = '';
            this.bookAuthor = '';
            this.createBookDialog = false;
          })
          .catch(error => {
            console.log(error);
          });
    },
    createAuthor() {
      api.createAuthor(this.authorFirstname, this.authorLastname)
          .then(response => {
            let newAuthor = response.data.data;
            this.authors.push(newAuthor);
            this.bookAuthors.push(newAuthor.firstName + ' ' + newAuthor.lastName);
            this.authorFirstname = '';
            this.authorLastname = '';
            this.createAuthorDialog = false;
          })
          .catch(error => {
            console.log(error);
          });
    }
  }
}
</script>
<style scoped>

</style>
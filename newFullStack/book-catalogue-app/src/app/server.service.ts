import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Book} from './books/book.model';

@Injectable()
export class ServerService {
  constructor(private http: Http) {}
  login(user: any) {
    return this.http.post('api/login', user);
  }
  getBooksFromServer() {
    return this.http.get('api/home');
  }

  getFavouriteBooksFromServer() {
    return this.http.get('api/getFavorites');
  }

  deleteFavouritebook(book: Book) {
    return this.http.post('api/deleteFavorite', book);
  }

  addBookToFavourite(book: Book) {
    return this.http.post('api/addFavorite', book);
  }
  deleteFavouritewithId(id: number) {
    return this.http.post('api/deleteFavouriteBook', id);
  }
}

export class Book {
  public id: number;
  public author: string;
  public year: string;
  public name: string;
  public isbn: string;
  public favorite: boolean;

  constructor(id: number, author: string, year: string,  name: string, isbn: string, favo: boolean) {
    this.id = id;
    this.name = name;
    this.year = year;
    this.author = author;
    this.isbn = isbn;
    this.favorite = favo;
  }
}

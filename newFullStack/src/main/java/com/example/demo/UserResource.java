package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//@CrossOrigin(origins="http://localhost:4200",maxAge = 3600)
//@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
@RequestMapping("/api")
public class UserResource {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    HttpSession httpSession;

    @GetMapping("/home")
    public List<Book> home(){

        List<Book> all = this.bookRepository.findAll(orderBy());
        User tmp = (User)httpSession.getAttribute("user");
        User user = this.userRepository.findByUsername(tmp.getUsername());
        for(int i = 0; i < all.size(); ++i) {
            if (user.getBooks().contains(all.get(i))){
                all.get(i).setFavorite(true);
            }
        }
        return all;
    }
    private Sort orderBy() {
        return new Sort(Sort.Direction.ASC, "year");
    }

//    @PostMapping("/login")
//    public User login(@RequestBody User user){
//        System.out.println(user.getUsername());
//        User tmp = userRepository.findByUsername(user.getUsername());
//        if(Objects.nonNull(tmp)){
//            if(tmp.getPassword().equals(user.getPassword())){
//                System.out.println(user.toString());
//                this.httpSession.setAttribute("user",user);
//                return tmp;
//            }
//        }
//        return null;
//    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        System.out.println(user.getUsername());
        User tmp = userRepository.findByUsername(user.getUsername());
        if(Objects.nonNull(tmp)){
            if(tmp.getPassword().equals(user.getPassword())){
                System.out.println(user.toString());
                this.httpSession.setAttribute("user",user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/getFavorites")
    public List<Book> getFavorite(){
        User user = (User) this.httpSession.getAttribute("user");
        return new ArrayList<>((this.userRepository
                                    .findByUsername(user.getUsername())
                                    .getBooks()));

    }

    /*
        add favorites list current book
     */
    @PostMapping(value = "/addFavorite")
    public void addFavorite(@RequestBody Book book){
        User tmpUser = (User) this.httpSession.getAttribute("user");
        User user = this.userRepository.findByUsername(tmpUser.getUsername());
        if(Objects.nonNull(user)){
            user.getBooks().add(book);
            this.userRepository.save(user);
            System.out.println("addFavorite " + user.getBooks());
        }else{
            System.out.println("there is no user with same name : " + user.getUsername());
        }
    }
    @PostMapping(value = "/isFavoriteCurrentBook")
    public boolean isFavoriteCurrentBook(@RequestBody Book book){
        //Book tmpbook = this.bookRepository.findByName(book.getName());
        User tmp = (User)httpSession.getAttribute("user");
        User user = this.userRepository.findByUsername(tmp.getUsername());
        if(user.getBooks().contains(book)){
            return true;
        }else{
            return false;
        }
    }
//    @GetMapping(value = "/isFavoriteCurrentBook")
//    public void isFavoriteCurrentBook(@RequestBody Book book){
//        // Book tmpbook = this.bookRepository.findByName(book.getName());
//        User tmp = (User)httpSession.getAttribute("user");
//        User user = this.userRepository.findByUsername(tmp.getUsername());
//        if(user.getBooks().contains(book)){
//            book.setFavorite(true);
//        }else{
//            book.setFavorite(false);
//        }
//    }
    @PostMapping(value = "/deleteFavorite")
    public void deleteFavorite(@RequestBody Book book){
        Book tmpbook = this.bookRepository.findByName(book.getName());
        User tmp = (User)httpSession.getAttribute("user");
        User user = this.userRepository.findByUsername(tmp.getUsername());
        user.getBooks().remove(tmpbook);
        this.userRepository.save(user);
        System.out.println(user.getBooks());
    }

    /**/
    @PostMapping("/deleteFavouriteBook")
    @Transactional
    public String deleteFavourite(@RequestBody String id){
        System.out.println("deleteFavourite " + id);

        User currentUser =(User) httpSession.getAttribute("user");
        User user = this.userRepository.findByUsername(currentUser.getUsername());
        System.out.println("deleteFavourite " + user);
        int ID = Integer.parseInt(id);
        System.out.println("deleteFavourite " + ID);

        Book book = bookRepository.findById(ID);
        System.out.println("deleteFavourite " + book);
        user.getBooks().remove(book);
        userRepository.save(user);
        return "Book with id: " + id + " will be deleted";
    }
    /**/
}

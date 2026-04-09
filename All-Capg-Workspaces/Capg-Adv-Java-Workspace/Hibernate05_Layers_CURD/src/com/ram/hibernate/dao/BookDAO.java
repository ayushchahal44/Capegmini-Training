package com.ram.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.ram.hibernate.entity.BookBean;

public class BookDAO {

    SessionFactory sf = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    /* CREATE */
    public void addBook(BookBean book) {

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        session.save(book);

        tx.commit();
        session.close();
    }

    /* READ */
    public BookBean getBook(int bookId) {

        Session session = sf.openSession();
        BookBean book = (BookBean) session.get(BookBean.class, bookId);
        session.close();

        return book;
    }

    /* UPDATE */
    public void updateBook(BookBean book) {

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        session.update(book);

        tx.commit();
        session.close();
    }

    /* DELETE */
    public void deleteBook(int bookId) {

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        BookBean book = (BookBean) session.get(BookBean.class, bookId);

        if (book != null) {
            session.delete(book);
        }

        tx.commit();
        session.close();
    }
}

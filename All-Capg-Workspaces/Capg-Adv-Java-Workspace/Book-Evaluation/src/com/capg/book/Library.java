package com.capg.book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {

    ArrayList<BookBean> bobj = new ArrayList<>();

    public void addBook(BookBean book) {
        bobj.add(book);
    }

    public List<BookBean> findLatestBooks(int yearThreshold) {

        return bobj.stream()
                .filter(b -> Integer.parseInt(b.getYearPublished()) > yearThreshold)
                .collect(Collectors.toList());
    }

    public List<BookBean> findBooksByComplexCriteria(int year, int minPages, String authorSubstring) {

        return bobj.stream()
                .filter(b -> Integer.parseInt(b.getYearPublished()) > year)
                .filter(b -> b.getNumberOfPages() >= minPages)
                .filter(b -> b.getAuthor().toLowerCase()
                        .contains(authorSubstring.toLowerCase()))
                .collect(Collectors.toList());
    }

}
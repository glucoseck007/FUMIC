package fpt.edu.fumic.interfaces;

import fpt.edu.fumic.database.entity.BookEntity;

/*
 * luong_123
 */
public interface BrowseBook {
    void onAccept(int p, BookEntity book);
    void onRefuse(int p, BookEntity book);
}

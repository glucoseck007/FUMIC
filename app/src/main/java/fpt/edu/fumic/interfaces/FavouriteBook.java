package fpt.edu.fumic.interfaces;

import fpt.edu.fumic.database.entity.BookEntity;

/*
 * luong_123
 */
public interface FavouriteBook {
    void onDelete(int p, BookEntity book);
    void onClick(int p,BookEntity book);

}

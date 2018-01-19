package com.hanyang.courses.dao;

import com.hanyang.courses.exc.DaoException;
import com.hanyang.courses.model.Review;

import java.util.List;

public interface ReviewDao {
    void add(Review review) throws DaoException;

    List<Review> findAll();

    List<Review> findByCourseId(int courseId);
}

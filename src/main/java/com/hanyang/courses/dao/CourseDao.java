package com.hanyang.courses.dao;

import com.hanyang.courses.exc.DaoException;
import com.hanyang.courses.model.Course;

import java.util.List;

public interface CourseDao {
    void add(Course course) throws DaoException;

    List<Course> findAll();

    Course findById(int id);
}

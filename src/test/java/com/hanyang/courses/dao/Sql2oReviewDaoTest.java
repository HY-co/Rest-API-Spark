package com.hanyang.courses.dao;

import com.hanyang.courses.dao.Sql2oCourseDao;
import com.hanyang.courses.dao.Sql2oReviewDao;
import com.hanyang.courses.exc.DaoException;
import com.hanyang.courses.model.Course;
import com.hanyang.courses.model.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oReviewDaoTest {
    private Sql2oReviewDao reviewDao;
    private Sql2oCourseDao courseDao;
    private Connection conn;
    private Course course;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:test;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o);
        courseDao = new Sql2oCourseDao(sql2o);
        // Keep connection open through entire test so that it won't be wiped out
        conn = sql2o.open();
        course = new Course("Test", "http://test.com");
        courseDao.add(course);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingReviewSetsId() throws Exception{
        Review review = new Review(course.getId(), 5, "");
        int originalReviewId = review.getId();

        reviewDao.add(review);
        assertNotEquals(originalReviewId, review.getId());
    }

    @Test
    public void addedReviewsAreReturnedFromFindAll() throws Exception {
        Review review = new Review(course.getId(), 5, "");
        reviewDao.add(review);

        assertEquals(1, reviewDao.findAll().size());
    }

    @Test
    public void noReviewsReturnEmptyList() throws Exception{
        assertEquals(0, reviewDao.findAll().size());
    }

    @Test
    public void multipleReviewsareFoundWhenTheyExistForACourse() throws Exception{
        reviewDao.add(new Review(course.getId(), 5, "Test Comment 1"));
        reviewDao.add(new Review(course.getId(), 2, "Test Comment 2"));

        List<Review> foundReview = reviewDao.findByCourseId(course.getId());

        assertEquals(2, foundReview.size());
    }

    @Test(expected = DaoException.class)
    public void addingAReviewToANonexistingCourseFails() throws Exception{
        reviewDao.add(new Review(40, 5, "Test Comment 1"));
    }
}

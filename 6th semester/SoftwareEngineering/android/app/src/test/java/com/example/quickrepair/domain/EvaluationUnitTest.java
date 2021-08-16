package com.example.quickrepair.domain;

import com.example.quickrepair.domain.Address;
import com.example.quickrepair.domain.Evaluation;
import com.example.quickrepair.domain.Repair;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EvaluationUnitTest
{
    Evaluation evaluation;

    @Before
    public void setUp()
    {
        evaluation = new Evaluation();
    }

    @Test(expected = NullPointerException.class)
    public void nullRepair()
    {
        evaluation.setRepair(null);
    }

    @Test
    public void okRepair()
    {
        evaluation.setRepair(new Repair());
        Assert.assertEquals(new Repair(), evaluation.getRepair());
    }

    @Test(expected = NullPointerException.class)
    public void nullTitle()
    {
        evaluation.setTitle(null);
    }

    @Test
    public void emptyTitle()
    {
        evaluation.setTitle("");
        Assert.assertEquals("No title", evaluation.getTitle());
    }

    @Test
    public void okTitle()
    {
        evaluation.setTitle("nn");
        Assert.assertEquals("nn", evaluation.getTitle());
    }

    @Test(expected = NullPointerException.class)
    public void nullComment()
    {
        evaluation.setComment(null);
    }

    @Test
    public void emptyComment()
    {
        evaluation.setComment("");
        Assert.assertEquals("No comment", evaluation.getComment());
    }

    @Test
    public void okComment()
    {
        evaluation.setComment("Comment");
        Assert.assertEquals("Comment", evaluation.getComment());
    }

    @Test
    public void okRate()
    {
        evaluation.setRate(5);
        Assert.assertEquals(5, evaluation.getRate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void biggerRate()
    {
        evaluation.setRate(6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void smallerRate()
    {
        evaluation.setRate(0);
    }

    @Test
    public void okUid()
    {
        evaluation.setUid(100);
        Assert.assertEquals(100, evaluation.getUid());
    }

    @Test
    public void equalsTest()
    {
        Repair repair = new Repair();

        evaluation.setRepair(repair);
        evaluation.setTitle("Title_1");
        evaluation.setComment("Comment_1");
        evaluation.setRate(3);

        Assert.assertEquals(true, evaluation.equals(new Evaluation(repair, "Title_1", "Comment_1", 3)));
        Assert.assertEquals(false, evaluation.equals(new Evaluation(repair, "Title", "Comment_1", 3)));
        Assert.assertEquals(false, evaluation.equals(new Evaluation(repair, "Title_1", "Comment", 3)));
        Assert.assertEquals(false, evaluation.equals(new Evaluation(repair, "Title_1", "Comment_1", 4)));
        Assert.assertEquals(false, evaluation.equals(null));
        Assert.assertEquals(true, evaluation.equals(evaluation));
    }

    @Test
    public void hashcodeTest()
    {
        Assert.assertEquals(923521, evaluation.hashCode());
    }
}

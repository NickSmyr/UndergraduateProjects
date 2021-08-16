package com.example.quickrepair.domain;

import java.util.Objects;

/**
 * Customer's Evaluation about an Repair
 */
public class Evaluation
{
    private int uid;
    private Repair repair;
    private String title;
    private String comment;
    private int rate;

    /**
     * Empty Constructor
     */
    public Evaluation()
    {
    }

    /**
     * Evaluation's Constructor
     *
     * @param title   evaluation's title
     * @param comment evaluation's comments and details about repair and technician's job in general
     * @param rate    evaluation's rate, [1, 5]
     */
    public Evaluation(Repair repair, String title, String comment, int rate)
    {
        setRepair(repair);
        setTitle(title);
        setComment(comment);
        setRate(rate);
    }

    //Setters and Getters

    /**
     * Get the title of the evaluation.
     *
     * @return The evaluation's title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Set evaluation's title
     *
     * @param title evaluation's title
     */
    public void setTitle(String title)
    {
        if (title == null) throw new NullPointerException("null title");

        if (title.length() > 0)
        {
            this.title = title;
        }
        else
        {
            this.title = "No title";
        }
    }

    /**
     * return evaluation's comment
     */
    public String getComment()
    {
        return comment;
    }

    /**
     * set evaluation's comment
     *
     * @param comment evaluation's comment
     */
    public void setComment(String comment)
    {
        if (comment == null) throw new NullPointerException("null comment");

        if (comment.length() > 0)
        {
            this.comment = comment;
        }
        else
        {
            this.comment = "No comment";
        }
    }

    /**
     * Get the rating of the evaluation.
     *
     * @return evaluation rate
     */
    public int getRate()
    {
        return rate;
    }

    /**
     * Set evaluation's rate.
     *
     * @param rate evaluation's rate, [1, 5]
     */
    public void setRate(int rate)
    {
        if (rate < 1 || rate > 5) throw new IllegalArgumentException("[1,5] stars");

        this.rate = rate;
    }

    /**
     * Get the evaluation UID.
     *
     * @return The UID.
     */
    public int getUid()
    {
        return uid;
    }

    /**
     * Set the evaluation UID.
     *
     * @param uid The evaluation's UID.
     */
    public void setUid(Integer uid)
    {
        this.uid = uid;
    }

    /**
     * Get the repair associated with the evaluation.
     *
     * @return The repair associated with the evaluation.
     */
    public Repair getRepair()
    {
        return repair;
    }

    /**
     * Set the repair associated with the evaluation.
     *
     * @param repair The repair associated with the evaluation.
     */
    public void setRepair(Repair repair)
    {
        if (repair == null) throw new NullPointerException("Repair can not be null.");

        this.repair = repair;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evaluation that = (Evaluation) o;
        return rate == that.rate &&
                Objects.equals(repair, that.repair) &&
                Objects.equals(title, that.title) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(repair, title, comment, rate);
    }
}

package com.example.quickrepair.domain;

import java.util.ArrayList;

public class Schedule
{
    ArrayList<ScheduleEntry> entries;

    /**
     * Default constructor.
     */
    public Schedule()
    {
        entries = new ArrayList<>();

        for (int i = 0; i < 7; ++i)
        {
            entries.add(new ScheduleEntry());
        }
    }

    /**
     * Set the starting and ending hours of the given day.
     *
     * @param day The day to set the schedule.
     * @param startingHour The starting hour.
     * @param endingHour The ending hour.
     */
    public void setSchedule(int day, int startingHour, int endingHour)
    {
        if (day < 1 || day > 7) throw new IllegalArgumentException("Day should be between one and seven inclusive.");

        if (startingHour < 0 || startingHour > 24)  throw new IllegalArgumentException("Day should be between zero and twenty three inclusive.");

        if (endingHour < 0 || endingHour > 24)  throw new IllegalArgumentException("Day should be between zero and twenty three inclusive.");

        entries.set(day - 1, new ScheduleEntry(startingHour, endingHour));
    }

    /**
     * Get the schedule entry of the given day.
     *
     * @param day the dy to get the schedule.
     * @return The day's schedule.
     */
    public ScheduleEntry getSchedule(int day)
    {
        if (day < 1 || day > 7) throw new IllegalArgumentException("Day should be between one and seven inclusive");

        return new ScheduleEntry(entries.get(day - 1));
    }

    public static class ScheduleEntry
    {
        private int startingHour;
        private int endingHour;

        /**
         * Default constructor.
         */
        public ScheduleEntry()
        {
            startingHour = 0;
            endingHour  = 0;
        }

        /**
         * Construct a ScheduleEntry with the given hours.
         *
         * @param startingHour The starting hour.
         * @param endingHour the ending hour.
         */
        public ScheduleEntry(int startingHour, int endingHour)
        {
            this.startingHour = startingHour;
            this.endingHour = endingHour;
        }

        /**
         * Copy constructor.
         *
         * @param entry The entry to copy.
         */
        public ScheduleEntry(ScheduleEntry entry)
        {
            this.startingHour = entry.startingHour;
            this.endingHour = entry.endingHour;
        }

        /**
         * Get the entry's starting hour.
         *
         * @return The entry's starting hour.
         */
        public int getStartingHour()
        {
            return startingHour;
        }

        /**
         * Get the entry's ending hour.
         *
         * @return The entry's ending hour.
         */
        public int getEndingHour()
        {
            return endingHour;
        }
    }
}

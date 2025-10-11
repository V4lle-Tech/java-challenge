package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class Test26_MeetingRoomsIITest {

    @Test
    void shouldReturnMinimumRoomsNeeded() {
        int[][] intervals = { { 0, 30 }, { 5, 10 }, { 15, 20 } };
        assertEquals(2, MeetingRoomsII.minMeetingRooms(intervals));
    }
}

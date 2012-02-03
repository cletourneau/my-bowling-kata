import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameTest {

    private BowlingGame game;

    @Before
    public void setUp() throws Exception {
        game = new BowlingGame();
    }

    private void roll(int numberOfRolls, int pinsDown) {
        for (int i = 0; i < numberOfRolls; ++i) {
            game.playerRolled(pinsDown);
        }
    }

    @Test
    public void testGutterGameShouldReturnAScoreOfZero() {
        roll(20, 0);
        assertEquals(0, game.score());
    }

    @Test
    public void testOnlyOnePinDownShouldReturnAScoreOfOne() {
        game.playerRolled(1);
        roll(19, 0);
        assertEquals(1, game.score());
    }

    @Test
    public void testSpareAddsValueOfNextRoll() {
        game.playerRolled(5);
        game.playerRolled(5);
        game.playerRolled(8);
        roll(17, 0);
        assertEquals(10 + 8 + 8, game.score());
    }

    @Test
    public void testThatAZeroRollFollowedByAStrikeShouldNotCountAsASpare() {
        // first frame
        game.playerRolled(0);
        game.playerRolled(0);

        // strike
        game.playerRolled(10);

        // next frame
        game.playerRolled(8);
        game.playerRolled(1);
        roll(14, 0);
        assertEquals(10 + 8 + 1 + 8 + 1, game.score());
    }

    @Test
    public void testThatAStrikeFollowedByAZeroRollShouldNotCountAsASpare() {
        // first frame
        game.playerRolled(0);
        game.playerRolled(0);

        // strike
        game.playerRolled(10);

        // next frame
        game.playerRolled(0);
        game.playerRolled(1);
        roll(14, 0);
        assertEquals(10 + 0 + 1 + 1, game.score());
    }


    @Test
    public void testStrikeAddsValueOfNextTwoRolls() {
        game.playerRolled(10);
        game.playerRolled(2);
        game.playerRolled(6);
        roll(16, 0);
        assertEquals(10 + 2 + 6 + 2 + 6, game.score());
    }

    @Test
    public void testLastBonusRollsOnStrikeCountsOnlyAsBonusPins() {
        roll(18, 0);
        game.playerRolled(10);
        game.playerRolled(10);
        game.playerRolled(10);
        assertEquals(10 + 10 + 10, game.score());
    }

    @Test
    public void testLastBonusRollOnSpareCountsOnlyAsBonusPins() {
        roll(18, 0);
        game.playerRolled(5);
        game.playerRolled(5);
        game.playerRolled(8);
        assertEquals(5 + 5 + 8, game.score());
    }


    @Test
    public void testPerfectGameShouldScoreThreeHundred() {
        roll(12, 10);
        assertEquals(300, game.score());
    }
}
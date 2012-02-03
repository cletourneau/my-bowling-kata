public class BowlingGame {
    private final int[] pinsDownPerRoll = new int[21];
    private int rollCount = 0;

    public void playerRolled(final int pinsDown) {
        pinsDownPerRoll[rollCount++] = pinsDown;
    }

    public int score() {
        int score = 0;
        for (int rollIndex = 0; rollIndex < rollCount; rollIndex++) {
            final int pinsDown = pinsDownPerRoll[rollIndex];

            score += pinsDown;
            score += spareBonus(rollIndex);
            score += strikeBonus(rollIndex);
        }

        score -= bonusPinsOnLastFrame();

        return score;
    }

    private int spareBonus(final int rollIndex) {
        if ((rollIndex > 1) && rollIsSpare(rollIndex - 1)) {
            return pinsDownPerRoll[rollIndex];
        }
        return 0;
    }

    private int strikeBonus(final int rollIndex) {
        if ((rollIndex > 1) && rollsIsStrike(rollIndex - 2)) {
            return pinsDownPerRoll[rollIndex] + pinsDownPerRoll[rollIndex - 1];
        }
        return 0;
    }

    private boolean rollIsSpare(final int rollIndex) {
        if (rollsIsStrike(rollIndex) || rollsIsStrike(rollIndex - 1))
            return false;

        return pinsDownPerRoll[rollIndex] + pinsDownPerRoll[rollIndex - 1] == 10;
    }

    private boolean rollsIsStrike(final int rollIndex) {
        return pinsDownPerRoll[rollIndex] == 10;
    }

    private int bonusPinsOnLastFrame() {
        if (rollsIsStrike(rollCount - 3)) {
            return pinsDownPerRoll[rollCount - 1] + pinsDownPerRoll[rollCount - 2];
        }

        if (rollIsSpare(rollCount - 2)) {
            return pinsDownPerRoll[rollCount - 1];
        }
        return 0;
    }
}

export enum Difficulty {
    EASY = 'EASY',
    MEDIUM = 'MEDIUM',
    HARD = 'HARD',
}

export const Difficulty2Label: Record<Difficulty, string> = {
    [Difficulty.EASY]: 'Leicht',
    [Difficulty.MEDIUM]: 'Mittel',
    [Difficulty.HARD]: 'Schwer'
};

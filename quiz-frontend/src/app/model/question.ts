import { Difficulty } from './difficulty.enum';
import { Domain } from './domain';
import { Answer } from './answer';

export class Question {
    id: string;
    title: string;
    text: string;
    image: string;
    createdBy: string;
    createdAt: Date;
    difficulty: Difficulty;

    domain: Domain;

    answers: Answer[];

    public constructor(id: string, title: string, image: string, text: string, createdBy: string, createdAt: Date, difficulty: Difficulty,
                       domain: Domain) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.difficulty = difficulty;
        this.domain = domain;
		this.image = image;
    }
}

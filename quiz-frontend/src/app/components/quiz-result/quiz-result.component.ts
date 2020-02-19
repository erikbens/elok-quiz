import { Component, OnInit } from '@angular/core';
import { QuizResult } from 'src/app/model/quizresult';
import { QuizService } from 'src/app/service/quiz.service';
import { Highscore } from 'src/app/model/highscore';

@Component({
	selector: 'app-quiz-result',
	templateUrl: './quiz-result.component.html',
	styleUrls: ['./quiz-result.component.less']
})
export class QuizResultComponent implements OnInit {

	quizResult: QuizResult;
	highscore: Highscore;

	constructor(private quizService: QuizService) {
		this.quizResult = history.state.quizResult as QuizResult;
	}

	ngOnInit() {
		this.saveQuizResult();
	}

	saveQuizResult() {
		this.quizService.submitQuizResult(this.quizResult).subscribe(resultData => {
			this.highscore = resultData;
		});
	}

}

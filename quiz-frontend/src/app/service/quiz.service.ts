import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { QuizFilter } from '../model/quizfilter';
import { Observable } from 'rxjs';
import { Question } from '../model/question';
import { Highscore } from '../model/highscore';
import { QuizResult } from '../model/quizresult';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  private baseUrl = environment.restUrl + '/quiz';
  private headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  public getQuizByQuestionFilter(quizFilter: QuizFilter): Observable<Question[]> {
    return this.http.post<Question[]>(this.baseUrl, quizFilter, { headers: this.headers });
  }

	public submitQuizResult(quizResult: QuizResult): Observable<Highscore> {
		return this.http.post<Highscore>(this.baseUrl + '/submit', quizResult, { headers: this.headers })
	}
}

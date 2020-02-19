import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Answer } from '../model/answer';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  private baseUrl = environment.restUrl + '/answers';
  private baseUrlWithQuestion = 'http://localhost:8888/questions';
  private headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  public findById(answerId: string): Observable<Answer> {
    return this.http.get<Answer>(this.baseUrl + '/' + answerId, { headers: this.headers });
  }

  public findAnswersByQuestionId(questionId: string): Observable<Answer[]> {
    return this.http.get<Answer[]>(this.baseUrlWithQuestion + '/' + questionId + '/answers', { headers: this.headers });
  }

  public deleteAnswer(answerId: string): Observable<any> {
    return this.http.delete(this.baseUrl + '/' + answerId, { headers: this.headers });
  }

  public save(questionId: string, answer: Answer): Observable<Answer> {
    if (answer.id === '') {
      return this.http.post<Answer>(this.baseUrlWithQuestion + '/' + questionId + '/answers', answer, { headers: this.headers });
    } else {
      return this.http.put<Answer>(this.baseUrl + '/' + answer.id, answer, { headers: this.headers });
    }
  }
}

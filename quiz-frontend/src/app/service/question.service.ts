import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Question } from '../model/question';
import { environment } from '../../environments/environment';
import { AuthenticationService } from './auth.service';
import { tap } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class QuestionService {

	private baseUrl = environment.restUrl + '/questions';
	private headers = new HttpHeaders().set('Content-Type', 'application/json');

	constructor(private http: HttpClient,
		private authService: AuthenticationService) { }

	public findAll(): Observable<Question[]> {
		return this.http.get<Question[]>(this.baseUrl, { headers: this.headers });
	}

	public findById(id: string): Observable<Question> {
		return this.http.get<Question>(this.baseUrl + '/' + id, { headers: this.headers });
	}

	public findByTitleOrText(query: string): Observable<Question[]> {
		return this.http.get<Question[]>(this.baseUrl + '/search', { headers: this.headers, params: { query } });
	}

	public save(question: Question, image: File): Observable<Question> {
		let formdata: FormData = new FormData();
		const questionBlob = new Blob([JSON.stringify(question)], { type: "application/json" });
		formdata.append('question', questionBlob);
		if (image != null) {
			formdata.append('image', image);
		}
		if (question.id === '') {
			question.createdBy = this.authService.currentUserValue.name;

			return this.http.post<Question>(this.baseUrl, formdata);
		} else {
			return this.http.put<Question>(this.baseUrl + '/' + question.id, formdata);
		}
	}

	public delete(id: string): Observable<void> {
		return this.http.delete<void>(this.baseUrl + '/' + id, { headers: this.headers });
	}

	public checkAnswersForQuestionId(questionId: string, answerIds: string[]): Observable<boolean> {
		return this.http.post<boolean>(this.baseUrl + '/' + questionId + '/check', answerIds, { headers: this.headers });
	}
	
	public findImageByQuestionId(questionId: string): Observable<Blob> {
		return this.http.get(this.baseUrl + '/' + questionId + '/image', { responseType: 'blob' });
	}
}

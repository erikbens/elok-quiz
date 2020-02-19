import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { environment } from "../../environments/environment";

@Injectable({
	providedIn: 'root'
})
export class UserService {

	private usersUrl = environment.restUrl + '/users';
	private headers = new HttpHeaders().set('Content-Type', 'application/json');

	constructor(private http: HttpClient) {
	}

	public findAll(): Observable<User[]> {
		return this.http.get<User[]>(this.usersUrl);
	}

	public findById(userId: string): Observable<User> {
		return this.http.get<User>(this.usersUrl + '/' + userId, { headers: this.headers });
	}

	public save(user: User): Observable<User> {
		if (user.id === '') {
			return this.http.post<User>(this.usersUrl, user, { headers: this.headers });
		} else {
			return this.http.put<User>(this.usersUrl + '/' + user.id, user, { headers: this.headers });
		}
	}

}

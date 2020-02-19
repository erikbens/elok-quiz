import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Domain } from '../model/domain';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DomainService {

  private domainsUrl = environment.restUrl + '/domains';
  private headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  public findAll(): Observable<Domain[]> {
    return this.http.get<Domain[]>(this.domainsUrl, { headers: this.headers });
  }

  public findById(domainId: string): Observable<Domain> {
    return this.http.get<Domain>(this.domainsUrl + '/' + domainId, { headers: this.headers } );
  }

  public save(domain: Domain): Observable<Domain> {
    if (domain.id === '') {
      return this.http.post<Domain>(this.domainsUrl, domain, { headers: this.headers } );
    } else {
      return this.http.put<Domain>(this.domainsUrl + '/' + domain.id, domain, { headers: this.headers } );
    }
  }

  public removeQuestionFromDomain(domainId: string, questionId: string): Observable<any> {
    return this.http.delete(this.domainsUrl + '/' + domainId + '/questions/' + questionId, { headers: this.headers });
  }

}

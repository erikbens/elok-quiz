import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserService } from './service/user.service';
import { DomainListComponent } from './components/domain-list/domain-list.component';
import { DomainService } from './service/domain.service';
import { DomainFormComponent } from './components/domain-form/domain-form.component';
import { QuestionService } from './service/question.service';
import { QuestionFormComponent } from './components/question-form/question-form.component';
import { QuestionListComponent } from './components/question-list/question-list.component';
import { EnumToArrayPipe } from './pipe/enumtoarray.pipe';
import { DatePipe } from '@angular/common';
import { AnswerListComponent } from './components/answer-list/answer-list.component';
import { AnswerService } from './service/answer.service';
import { AnswerFormComponent } from './components/answer-form/answer-form.component';
import { AlertService } from './alert';
import { AlertModule } from './alert';
import { QuizOverviewComponent } from './components/quiz-overview/quiz-overview.component';
import { QuizPlayComponent } from './components/quiz-play/quiz-play.component';
import { QuizService } from './service/quiz.service';
import { QuizResultComponent } from './components/quiz-result/quiz-result.component';

import { JwPaginationComponent } from 'jw-angular-pagination';
import { AuthenticationService } from './service/auth.service';
import { ErrorInterceptor } from './auth/error.interceptor';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { JwtInterceptor } from './auth/jwt.interceptor';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';

@NgModule({
	declarations: [
		AppComponent,
		UserListComponent,
		UserFormComponent,
		DomainListComponent,
		DomainFormComponent,
		QuestionFormComponent,
		QuestionListComponent,
		EnumToArrayPipe,
		AnswerListComponent,
		AnswerFormComponent,
		QuizOverviewComponent,
		QuizPlayComponent,
		QuizResultComponent,
		JwPaginationComponent,
		LoginFormComponent
	],
	imports: [
		BrowserModule,
		BrowserAnimationsModule,
		AppRoutingModule,
		FormsModule,
		HttpClientModule,
		ReactiveFormsModule,
		AlertModule,
		ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
	],
	providers: [
		{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
		{ provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },

		UserService, DomainService, QuestionService, DatePipe, AnswerService, AlertService, QuizService, AuthenticationService],
	bootstrap: [AppComponent]
})
export class AppModule { }

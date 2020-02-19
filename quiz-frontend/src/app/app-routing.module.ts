import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { DomainListComponent } from './components/domain-list/domain-list.component';
import { DomainFormComponent } from './components/domain-form/domain-form.component';
import { QuestionFormComponent } from './components/question-form/question-form.component';
import { QuestionListComponent } from './components/question-list/question-list.component';
import { AnswerFormComponent } from './components/answer-form/answer-form.component';
import { QuizOverviewComponent } from './components/quiz-overview/quiz-overview.component';
import { QuizPlayComponent } from './components/quiz-play/quiz-play.component';
import { QuizResultComponent } from './components/quiz-result/quiz-result.component';
import { LoginFormComponent } from './components/login-form/login-form.component';

const routes: Routes = [
	{ path: 'admin/users', component: UserListComponent, canActivate: [ AuthGuard ]  },
	{ path: 'admin/users/:userId', component: UserFormComponent, canActivate: [ AuthGuard ]  },
	{ path: 'admin/domains', component: DomainListComponent, canActivate: [ AuthGuard ] },
	{ path: 'admin/domains/:domainId', component: DomainFormComponent, canActivate: [ AuthGuard ]  },
	{ path: 'admin/questions/:questionId/answers/:answerId', component: AnswerFormComponent, canActivate: [ AuthGuard ]  },
	{ path: 'admin/questions/:questionId', component: QuestionFormComponent, canActivate: [ AuthGuard ]  },
	{ path: 'admin/questions', component: QuestionListComponent, canActivate: [ AuthGuard ]  },
	{ path: 'quiz/play', component: QuizPlayComponent },
	{ path: 'quiz/result', component: QuizResultComponent },
	{ path: 'quiz', component: QuizOverviewComponent },
	{ path: 'login', component: LoginFormComponent },
	{ path: 'register', component: UserFormComponent },
	{ path: '', component: QuizOverviewComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }

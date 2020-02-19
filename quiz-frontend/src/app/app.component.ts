import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './service/auth.service';
import { AuthUser } from './model/auth-user';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.less']
})
export class AppComponent {

	title = 'eLOK Quiz';
	authUser: AuthUser;

	constructor(public router: Router,
		private authService: AuthenticationService) {
		this.authService.currentUser.subscribe(resultData => this.authUser = resultData);
	}
	
	logout() {
		this.authService.logout();
		this.router.navigate(['/quiz']);
	}

}

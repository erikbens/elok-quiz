import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Location } from '@angular/common';
import { AlertService } from 'src/app/alert';
import { AuthenticationService } from '../../service/auth.service';

@Component({
	selector: 'app-login-form',
	templateUrl: './login-form.component.html',
	styleUrls: ['./login-form.component.less']
})
export class LoginFormComponent implements OnInit {

	loginForm: FormGroup;
	submitted = false;

	constructor(private formBuilder: FormBuilder,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private authService: AuthenticationService,
		private alertService: AlertService,
		private router: Router) {
		if (this.authService.currentUserValue) {
			this.router.navigate(['/quiz']);
		}
	}

	ngOnInit() {
		this.loginForm = this.formBuilder.group({
			name: new FormControl('', Validators.required),
			pass: new FormControl('', Validators.required),
		});
	}

	get f() { return this.loginForm.controls; }


	onSubmit() {
		this.submitted = true;

		if (this.loginForm.invalid) {
			return;
		}
		
		this.authService.login(this.loginForm.controls.name.value, this.loginForm.controls.pass.value).subscribe(resultData => {
			this.alertService.successKeepOnRouteChange('Erfoglreich eingeloggt.');
			this.router.navigate(['/quiz']);
		},
			error => {
				this.alertService.error(error);
			});
	}

	goBack() {
		this.location.back();
	}

}

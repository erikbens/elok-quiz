import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MustMatch } from 'src/app/validator/must-match.validator';
import { Location } from '@angular/common';
import { AlertService } from 'src/app/alert';

@Component({
	selector: 'app-user-form',
	templateUrl: './user-form.component.html',
	styleUrls: ['./user-form.component.less']
})
export class UserFormComponent implements OnInit {

	userForm: FormGroup;
	isRegister: boolean;
	submitted = false;

	constructor(private formBuilder: FormBuilder,
		private activatedRoute: ActivatedRoute,
		private location: Location,
		private userService: UserService,
		private alertService: AlertService) {
		this.activatedRoute.snapshot['_routerState'].url.includes('register') ? this.isRegister = true : this.isRegister = false;

	}

	ngOnInit() {
		if (!this.isRegister) {
			this.getUser();
		}
		this.userForm = this.formBuilder.group({
			id: [''],
			name: new FormControl('', Validators.required),
			pass: new FormControl('', [
				Validators.required, Validators.minLength(6)
			]),
			confirmPassword: new FormControl('', Validators.required),
			role: this.isRegister ? new FormControl({ value: 'USER', disabled: true }) : new FormControl('', Validators.required)
		}, {
			validator: MustMatch('pass', 'confirmPassword')
		});
	}

	get f() { return this.userForm.controls; }

	getUser(): void {
		const userId = this.activatedRoute.snapshot.paramMap.get('userId');
		if (userId !== '0') {
			this.userService.findById(userId).subscribe((user) => {
				this.userForm.setValue({
					id: user.id,
					name: user.name,
					pass: '',
					confirmPassword: '',
					role: user.role
				});
			},
				error => {
					console.log("Fehler ::" + error);
					this.alertService.error(error.message);
				});
		}
	}

	onSubmit() {
		this.submitted = true;

		if (this.userForm.invalid) {
			return;
		}

		this.userService.save(this.userForm.value).subscribe(resultData => {
			this.alertService.successKeepOnRouteChange('Nutzer \'' + resultData.name + '\' erolgreich gespeichert.');
			this.goBack();
		});
	}

	goBack() {
		this.location.back();
	}

}

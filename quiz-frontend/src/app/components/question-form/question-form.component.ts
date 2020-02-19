import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { QuestionService } from 'src/app/service/question.service';
import { Location, DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Difficulty, Difficulty2Label } from 'src/app/model/difficulty.enum';
import { Domain } from 'src/app/model/domain';
import { DomainService } from 'src/app/service/domain.service';
import { AlertService } from 'src/app/alert';

@Component({
	selector: 'app-question-form',
	templateUrl: './question-form.component.html',
	styleUrls: ['./question-form.component.less']
})
export class QuestionFormComponent implements OnInit {

	questionForm: FormGroup;
	domainForm: FormGroup;
	submitted = false;
	imageUrl: string;
	imageFile: File;

	difficulty2Label = Difficulty2Label;
	difficulties = Object.values(Difficulty);
	domains: Domain[];

	constructor(private formBuilder: FormBuilder,
		private questionService: QuestionService,
		private location: Location,
		private route: ActivatedRoute,
		private domainService: DomainService,
		private datePipe: DatePipe,
		private alertService: AlertService) { }

	ngOnInit() {
		this.getDomains();
		this.getQuestion();

		this.domainForm = this.formBuilder.group({
			id: new FormControl(null),
			name: ''
		});
		this.questionForm = this.formBuilder.group({
			id: '',
			domain: this.domainForm,
			title: new FormControl('', [Validators.required, Validators.minLength(10)]),
			text: new FormControl('', [Validators.required, Validators.minLength(10)]),
			difficulty: new FormControl('', [Validators.required]),
			image: new FormControl({ value: '', disabled: true }),
			createdAt: new FormControl({ value: '', disabled: true }),
			createdBy: new FormControl({ value: '', disabled: true })
		});
	}

	getDomains() {
		this.domainService.findAll().subscribe(res => {
			this.domains = res;
		});
	}

	getQuestion() {
		const questionId = this.route.snapshot.paramMap.get('questionId');
		if (questionId !== '0') {
			this.questionService.findById(questionId).subscribe(questionData => {
				this.questionForm.patchValue({
					id: questionData.id,
					title: questionData.title,
					text: questionData.text,
					image: questionData.image,
					difficulty: questionData.difficulty,
					createdAt: this.datePipe.transform(questionData.createdAt, 'dd.MM.yyyy'),
					createdBy: questionData.createdBy
				});
				if (questionData.domain !== null) {
					this.domainForm.patchValue(questionData.domain);
				}

				//get image if present
				if (questionData.image != null) {
					this.questionService.findImageByQuestionId(questionId).subscribe(resultFile => {
						// File Preview
						const previewReader = new FileReader();
						previewReader.readAsDataURL(resultFile)

						previewReader.onload = () => {
							this.imageUrl = previewReader.result as string;
							this.questionForm.get('image').updateValueAndValidity();
						}
					},
						error => {
							console.log(error);
						});
				}
			});
		}
	}

	get f() { return this.questionForm.controls; }

	get d() { return this.domainForm.controls; }

	onSubmit() {
		this.submitted = true;
		this.questionService.save(this.questionForm.getRawValue(), this.imageFile).subscribe(resultData => {
			this.alertService.successKeepOnRouteChange('Frage \'' + resultData.title + '\' erfolreich gespeichert.');
			this.goBack();
		});
	}

	goBack() { return this.location.back(); }

	onSelectFile(event) {
		if (event.target.files && event.target.files[0]) {
			const file = (event.target as HTMLInputElement).files[0];
			this.imageFile = file;
			this.questionForm.patchValue({
				image: file.name
			});

			// File Preview
			const previewReader = new FileReader();
			previewReader.readAsDataURL(file)

			previewReader.onload = () => {
				this.imageUrl = previewReader.result as string;
				this.questionForm.get('image').updateValueAndValidity();
			}
		}
	}

}

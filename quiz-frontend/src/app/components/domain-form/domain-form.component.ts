import { Component, OnInit } from '@angular/core';
import { DomainService } from 'src/app/service/domain.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup, FormControl, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Question } from 'src/app/model/question';
import { AlertService } from 'src/app/alert';
import { Domain } from 'src/app/model/domain';

@Component({
  selector: 'app-domain-form',
  templateUrl: './domain-form.component.html',
  styleUrls: ['./domain-form.component.less']
})
export class DomainFormComponent implements OnInit {

  domainForm: FormGroup;
  submitted = false;

  constructor(private domainService: DomainService,
              private route: ActivatedRoute,
              private location: Location,
              private formBuilder: FormBuilder,
              private alertService: AlertService) { }

  ngOnInit() {
    this.getDomain();
    this.domainForm = this.formBuilder.group({
      id: [''],
      name: new FormControl('', Validators.required),
      questions: this.formBuilder.array([])
    });
  }

  get f() { return this.domainForm.controls; }

  get questionArray() { return (this.domainForm.get('questions') as FormArray).controls;  }

  getDomain(): void {
    const domainId = this.route.snapshot.paramMap.get('domainId');
    if (domainId !== '0') {
      this.domainService.findById(domainId).subscribe(data => {
        this.domainForm.patchValue({
          id: data.id,
          name: data.name
        });
        data.questions.forEach(question => {
          this.questionArray.push(this.formBuilder.group({
            id: question.id,
            title: question.title,
            text: question.text,
            createdBy: question.createdBy,
            difficulty: question.difficulty
          }));
        });
      });
    }
  }

  getDomainId() { return this.domainForm.get('id').value; }

  onSubmit() {
    this.submitted = true;

    if (!this.domainForm.valid) {
      return;
    }
    this.domainService.save(this.domainForm.value).subscribe((resultData: Domain) => {
      this.alertService.successKeepOnRouteChange('Bereich \'' + resultData.name + '\' erfolgreich gespeichert.');
      this.goBack();
    });
  }

  goBack() {
    this.location.back();
  }

  getQuestionAt(i: number): Question {
    return (this.domainForm.get('questions') as FormArray).at(i).value;
  }

  removeQuestion(i: number) {
    const question = this.getQuestionAt(i);
    if (confirm('Frage \'' + question.title + '\' löschen?')) {
      this.domainService.removeQuestionFromDomain(this.getDomainId(), question.id).subscribe(() => {
        (this.domainForm.get('questions') as FormArray).removeAt(i);
      },
      error => console.log(error));
    }
  }

}

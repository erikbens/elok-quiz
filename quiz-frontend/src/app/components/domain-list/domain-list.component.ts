import { Component, OnInit } from '@angular/core';
import { Domain } from 'src/app/model/domain';
import { DomainService } from 'src/app/service/domain.service';

@Component({
  selector: 'app-domain-list',
  templateUrl: './domain-list.component.html',
  styleUrls: ['./domain-list.component.less']
})
export class DomainListComponent implements OnInit {

  domains: Domain[];

  constructor(private domainService: DomainService) { }

  ngOnInit() {
    this.domainService.findAll().subscribe(data => {
      this.domains = data;
    });
  }

}

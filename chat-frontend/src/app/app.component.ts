import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TestService } from './services/test.service';

@Component({
	selector: 'app-root',
	standalone: true,
	imports: [RouterOutlet, CommonModule],
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
	title = 'chat-frontend';
	message: string | undefined;

	constructor(private testService: TestService) {}

	ngOnInit(): void {
			this.testService.getTestMessage()
				.subscribe({
					next: (data) => this.message = data.message,
					error: (error) => console.error('Erreur de connexion', error)
			});
			
	}
}

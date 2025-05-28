import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
	email = '';
	password = '';

	constructor(private authService: AuthService, private router: Router) {}
	
	/**
	 * Handles the login process.
	 * It calls the AuthService to authenticate the user and stores the token in local storage.
	 * On successful login, it navigates to the chat page.
	 * If login fails, it shows an error message.
	 */
	login(): void {
		this.authService.login(this.email, this.password).subscribe({
			next: (response) => {
				localStorage.setItem('token', response.token);
				this.router.navigate(['/chat']);
			},
			error: (error) => {
				console.error('Login failed', error);
				alert('Login failed. Please check your credentials.');
			}
		});
	}
}

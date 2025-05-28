import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
	standalone: true,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
	email = '';
	password = '';
	confirmPassword = '';

	constructor(private authService: AuthService, private router: Router) {}
	
	/**
	 * Handles the registration process.
	 * It calls the AuthService to register the user and stores the token in local storage.
	 * On successful registration, it navigates to the chat page.
	 * If registration fails, it shows an error message.
	 */
	register(): void {
		if (this.password !== this.confirmPassword) {
			alert('Passwords do not match.');
			return;
		}

		this.authService.register(this.email, this.password).subscribe({
			next: (response) => {
				localStorage.setItem('token', response.token);
				this.router.navigate(['/login']);
			},
			error: (error) => {
				console.error('Registration failed', error);
				alert('Registration failed. Please try again.');
			}
		});
	}
}

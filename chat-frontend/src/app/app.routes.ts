import { Routes } from '@angular/router';
import { ChatRoomComponent } from './components/chat-room/chat-room.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'chat', pathMatch: 'full' }, // redirection par défaut vers /chat
	{ path: 'chat', component: ChatRoomComponent },
	{ path: 'register', component: RegisterComponent },
	{ path: 'login', component: LoginComponent }
];

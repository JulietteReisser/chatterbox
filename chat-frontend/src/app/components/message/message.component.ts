import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Message } from '../../models/message';

/**
 * The `MessageComponent` represents a single chat message.
 * It displays the sender information, message content and timestamp.
 */
@Component({
  selector: 'app-message',
	standalone: true,
	imports: [CommonModule],
  templateUrl: './message.component.html',
  styleUrl: './message.component.scss'
})
export class MessageComponent {
	/**
	 * The message object containing all message details.
	 */
	@Input() message!: Message;

	/**
	 * Determines whether the sender's name should be visible.
	 */
	@Input() showHeader!: boolean;

	/**
	 * Determines whether the timestamp should be visible.
	 */
	@Input() showTimestamp!: boolean;
}

import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { MessageComponent } from '../message/message.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Message } from '../../models/message';
import { MessageService } from '../../services/message.service';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';

/**
 * The `ChatRoomComponent` is responsible for managing the chat interface.
 * It handles user messages and displays them using `MessageComponent`.
 */
@Component({
  selector: 'app-chat-room',
	standalone: true,
  imports: [CommonModule, FormsModule, MessageComponent],
  templateUrl: './chat-room.component.html',
  styleUrl: './chat-room.component.scss'
})
export class ChatRoomComponent implements AfterViewInit {
	/**
	 * The current logged-in user.
	 */
	user: User | null = null;

	/**
	 * The list of chat messages displayed in the chat room.
	 */
	messages: Message[] = [];
	// messages = [
  //   { author: "Alice", content: "Hello!", avatar: "https://api.dicebear.com/7.x/bottts/svg?seed=alice", isOwnMessage: false, timestamp: new Date() },
  //   { author: "Alice", content: "How are you?", avatar: "https://api.dicebear.com/7.x/bottts/svg?seed=alice", isOwnMessage: false, timestamp: new Date() },
  //   { author: "You", content: "I'm good!", avatar: "https://api.dicebear.com/7.x/bottts/svg?seed=you", isOwnMessage: true, timestamp: new Date() }
  // ];

	/** The new message entered by the user. */
	newMessage: string = '';

	/**
	 * The time threshold (in milliseconds) after which a new timestamp should be displayed.
	 * - Default: `5 * 60 * 1000` (5 minutes).
	 */
	timestampTreshold = 5 * 60 * 1000;

	/**
	 * A reference to the message container div.
	 * Used for automatic scrolling when a new message is added.
	 */
	@ViewChild('messagesContainer') private messagesContainer!: ElementRef;

	constructor(private messageService: MessageService, private userService: UserService) {}

	/**
	 * Initializes the component by loading the user and fetching stored message.
	 */
	ngAfterViewInit(): void {
		this.userService.user$.subscribe(user => this.user = user);
		this.loadMessages();
	}

	/**
	 * Handles sending a new message.
	 */
	sendMessage() {
		if (!this.newMessage.trim() || !this.user) return;

		const newMsg: Message = {
			author: this.user.username,
			content: this.newMessage,
			avatar: this.user.avatar,
			timestamp: new Date(),
			isOwnMessage: true
		}

		this.messageService.sendMessage(newMsg).subscribe(savedMessage => {
			this.messages.push(savedMessage);
			this.newMessage = ''; // Clear the input field
			setTimeout(() => requestAnimationFrame(() => this.scrollToBottom()), 0); // Wait for full DOM update
		})
	}

	/**
	 * Clears all message from the chat room.
	 */
	clearMessages(): void {
		this.messageService.clearMessages().subscribe(() => this.messages = []);
	}

	/**
	 * Determines whether a message should display its timestamp.
	 * @param index The index of the current message in the `messages` arrays.
	 * @returns `true` if the timestamp should be displayed, otherwise `false`.
	 */
	shouldShowTimestamp(index: number): boolean {
		if (index === this.messages.length - 1) {
			return true; // Alway show the timestamp for the last message
		}

		const currentMessage = this.messages[index];
		const nextMessage = this.messages[index + 1];

		const currentTime = new Date(nextMessage.timestamp).getTime();
		const nextTime = new Date(currentMessage.timestamp).getTime();

		if (currentMessage.author !== nextMessage.author) {
      return true; // Show timestamp if the sender changes
    }

		if (nextTime - currentTime > this.timestampTreshold) {
			return true; // Show timestamp if next message was significantly later
		}

		// Check previous message to ensure timestamp isn't removed if a later message appears
		if (index > 0) {
			const previousMessage = this.messages[index - 1];
			const previousTime = new Date(previousMessage.timestamp).getTime();

			if (currentTime - previousTime > this.timestampTreshold) {
				return true; // Show timestamp if previous message was significantly earlier
			}
		}

		return false;
	}

	/**
	 * Determines whether the date separator should be displayed.
	 * @param index The index of the current message in the `messages` arrays.
	 * @returns `true` if the date header should be displayed, otherwise `false`.
	 */
	shouldShowDateHeader(index: number): boolean {
		if (index === 0) return true;

		const currentMessageDate = new Date(this.messages[index].timestamp).setHours(0, 0, 0, 0);
		const previousMessageDate = new Date(this.messages[index - 1].timestamp).setHours(0, 0, 0, 0);

		return currentMessageDate !== previousMessageDate;
	}

	/**
	 * Returns a formatted date string for a given timestamp.
	 * @param timestamp The date of the message.
	 * @returns A formatted string representing the message's date.
	 */
	getFormattedDate(timestamp: Date): string {
		const messageDate = new Date(timestamp);

		const today = new Date()
		today.setHours(0, 0, 0, 0);
		
		const yesterday = new Date(today);
		yesterday.setDate(today.getDate() - 1);

		const twoDaysAgo = new Date(today)
		twoDaysAgo.setDate(today.getDate() - 2);

		if (messageDate.getTime() >= today.getTime()) {
			return 'Aujourd\'hui';
		} else if (messageDate.getTime() >= yesterday.getTime()) {
			return 'Hier';
		} else if (messageDate.getTime() >= twoDaysAgo.getTime()) {
			return 'Avant-hier';
		}

		return new Intl.DateTimeFormat('fr-FR', {
			weekday: 'long',
			day: 'numeric',
			month: 'long',
			year: 'numeric'
		}).format(messageDate);
	}

	/**
	 * Loads the messages and updates the chat.
	 */
	private loadMessages(): void {
		this.messageService.getMessages()
			.subscribe(messages => {
				this.messages = messages.map(msg => ({
					...msg,
					timestamp: new Date(msg.timestamp)
				}));
			});
		
	}

	/**
	 * Automatically scrolls the chat to the latest message.
	 */
	private scrollToBottom() {
		if (this.messagesContainer) {
			this.messagesContainer.nativeElement.scrollTo({
				top: this.messagesContainer.nativeElement.scrollHeight,
				behavior: 'smooth'
			})
    }
	}
}

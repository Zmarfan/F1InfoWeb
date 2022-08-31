import {Component, ElementRef, HostListener, Input} from '@angular/core';
import {BellItem, MenuItem} from '../profile-header.component';
import {animate, style, transition, trigger} from '@angular/animations';
import {Session} from '../../../../app/configuration/session';

@Component({
    selector: 'app-desktop-profile-header',
    templateUrl: './desktop-profile-header.component.html',
    styleUrls: ['./desktop-profile-header.component.scss', '../profile-header.component.scss'],
    animations: [
        trigger('inOutAnimation', [
            transition(':enter', [
                style({ transform: 'translateY(-5px) scaleY(0.85)', opacity: 0 }),
                animate('0.25s ease-out', style({ transform: 'translateY(0) scaleY(1)', opacity: 1 })),
            ]),
            transition(':leave', [
                style({ transform: 'translateY(0) scaleY(1)', opacity: 1 }),
                animate('0.25s ease-in', style({ transform: 'translateY(-5px) scaleY(0.85)', opacity: 0 })),
            ]),
        ]),
        trigger('bellInOutAnimation', [
            transition(':enter', [
                style({ transform: 'translate(-50%, -5px) scaleY(0.85)', opacity: 0 }),
                animate('0.25s ease-out', style({ transform: 'translate(-50%, 0) scaleY(1)', opacity: 1 })),
            ]),
            transition(':leave', [
                style({ transform: 'translate(-50%, 0) scaleY(1)', opacity: 1 }),
                animate('0.25s ease-in', style({ transform: 'translate(-50%, -5px) scaleY(0.85)', opacity: 0 })),
            ]),
        ]),
    ],
})
export class DesktopProfileHeaderComponent {
    private static readonly ANIMATION_LENGTH_MILLISECONDS = 250;
    @Input() public loggedIn!: boolean;
    @Input() public menuItems!: MenuItem[];
    @Input() public bellItems!: BellItem[];
    @Input() public infoButtonCallback!: () => void;
    @Input() public bellItemsOpenedCallback!: () => void;

    public menuOpen: boolean = false;
    public bellOpen: boolean = false;
    private mLastTimeStamp: number = 0;

    public constructor(
        public session: Session,
        private mElement: ElementRef
    ) {
    }

    public get amountOfUnOpenedBellItems(): number {
        return this.bellItems.filter((item) => !item.opened).length;
    }

    @HostListener('document:click', ['$event'])
    public mouseClick(event: MouseEvent) {
        if (!this.mElement.nativeElement.contains(event.target)) {
            if (this.menuOpen) {
                this.menuOpen = false;
            }
            if (this.bellOpen) {
                this.setBellMenu(false);
            }

            this.mLastTimeStamp = event.timeStamp;
        }
    }

    public menuToggle(event: MouseEvent) {
        if (this.mLastTimeStamp + DesktopProfileHeaderComponent.ANIMATION_LENGTH_MILLISECONDS < event.timeStamp) {
            this.menuOpen = !this.menuOpen;
            if (this.bellOpen) {
                this.setBellMenu(false);
            }
            this.mLastTimeStamp = event.timeStamp;
        }
    }

    public bellToggle(event: MouseEvent) {
        if (this.mLastTimeStamp + DesktopProfileHeaderComponent.ANIMATION_LENGTH_MILLISECONDS < event.timeStamp) {
            this.setBellMenu(!this.bellOpen);
            this.menuOpen = false;
            this.mLastTimeStamp = event.timeStamp;
        }
    }

    private setBellMenu(open: boolean) {
        this.bellOpen = open;
        if (!this.bellOpen && this.amountOfUnOpenedBellItems > 0) {
            this.bellItemsOpenedCallback();
        }
    }
}

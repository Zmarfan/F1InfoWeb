import {Component, ElementRef, HostBinding, HostListener} from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faCalculator, faCircleHalfStroke, faEarthAfrica, faRightToBracket, faSailboat, faUserGraduate} from '@fortawesome/free-solid-svg-icons';

interface MenuItem {
    icon: IconDefinition;
    translationKey: string;
    clickCallback: () => void;
    cssClass?: string;
}

@Component({
    selector: 'app-profile-header',
    templateUrl: './profile-header.component.html',
    styleUrls: ['./profile-header.component.scss'],
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
    ],
})
export class ProfileHeaderComponent {
    private static readonly ANIMATION_LENGTH_MILLISECONDS = 250;

    public menuOpen: boolean = false;
    public userName: string = 'Lord_Zmarfan';
    public displayName: string = 'Anonymous User';
    public menuItems: MenuItem[] = [
        { icon: faEarthAfrica, translationKey: 'this.key.cool2', cssClass: 'display-menu__wide', clickCallback: () => console.log(1) },
        { icon: faCircleHalfStroke, translationKey: 'navigation.profile.darkMode', clickCallback: () => this.toggleDarkMode() },
        { icon: faSailboat, translationKey: 'this.key.cool4', clickCallback: () => console.log(2) },
        { icon: faRightToBracket, translationKey: 'navigation.profile.login', cssClass: 'display-menu__wide', clickCallback: () => console.log(4) },
    ];

    private mLastTimeStamp: number = 0;

    public constructor(private mElement: ElementRef) {
    }

    @HostListener('document:click', ['$event'])
    public mouseClick(event: MouseEvent) {
        if (!this.mElement.nativeElement.contains(event.target)) {
            this.menuOpen = false;
            this.mLastTimeStamp = event.timeStamp;
        }
    }

    public menuToggle(event: MouseEvent) {
        if (this.mLastTimeStamp + ProfileHeaderComponent.ANIMATION_LENGTH_MILLISECONDS < event.timeStamp) {
            this.menuOpen = !this.menuOpen;
            this.mLastTimeStamp = event.timeStamp;
        }
    }

    private toggleDarkMode() {
        document.body.classList.toggle('dark-theme');
    }
}

import {Component, ElementRef, HostListener, ViewChild} from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
})
export class AppComponent {
    @ViewChild('scrollBody') public scrollBody!: ElementRef;

    private mShowHeader: boolean = true;
    private mLastYPosition: number = 0;

    public get hideHeaderClass(): string {
        return this.mShowHeader ? 'header--show' : 'header--hide';
    }

    @HostListener('window:scroll')
    public onScroll() {
        const yPosition: number = this.scrollBody.nativeElement.getBoundingClientRect().top;
        const topOffPage: boolean = yPosition >= 0;
        const scrollingUp: boolean = yPosition >= this.mLastYPosition;
        this.mLastYPosition = yPosition;
        this.mShowHeader = topOffPage || scrollingUp;
    }
}

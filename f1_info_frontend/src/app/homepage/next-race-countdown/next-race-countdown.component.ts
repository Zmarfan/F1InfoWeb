import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-next-race-countdown',
    templateUrl: './next-race-countdown.component.html',
    styleUrls: ['./next-race-countdown.component.scss'],
})
export class NextRaceCountdownComponent implements OnInit {
    @Input() public nextRaceResponse: { nextSessionDate: Date, sessionInProgress: boolean } = {
        nextSessionDate: new Date(2022, 12, 14),
        sessionInProgress: false,
    };

    public days: number | string = '0';
    public hours: number | string = '00';
    public minutes: number | string = '00';
    public seconds: number | string = '00';

    public get sessionKey(): string {
        return 'missing.key';
    }

    public ngOnInit() {
        this.refreshCountdown();
        setInterval(() => this.refreshCountdown(), 1000);
    }

    private refreshCountdown() {
        const difference = this.nextRaceResponse.nextSessionDate.getTime() - new Date().getTime();
        this.seconds = Math.floor(difference / 1000);
        this.minutes = Math.floor(this.seconds / 60);
        this.hours = Math.floor(this.minutes / 60);
        this.days = Math.floor(this.hours / 24);

        this.hours %= 24;
        this.minutes %= 60;
        this.seconds %= 60;
        this.hours = this.hours < 10 ? '0' + this.hours : this.hours;
        this.minutes = this.minutes < 10 ? '0' + this.minutes : this.minutes;
        this.seconds = this.seconds < 10 ? '0' + this.seconds : this.seconds;
    }
}

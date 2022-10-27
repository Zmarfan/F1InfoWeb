import {Component, Input} from '@angular/core';
import {NextRaceInfoResponse, SessionInfo, SessionType} from '../../../../generated/server-responses';
import * as moment from 'moment';

interface Session {
    sessionType: SessionType;
    sessionTime: string;
    sessionDate: number;
    sessionMonth: string;
    selected: boolean;
    completed: boolean;
}

@Component({
    selector: 'app-next-race-times',
    templateUrl: './next-race-times.component.html',
    styleUrls: ['./next-race-times.component.scss'],
})
export class NextRaceTimesComponent {
    @Input() public nextRaceResponse!: NextRaceInfoResponse;
    public showMyTime: boolean = true;

    public get sessions(): Session[] {
        return this.nextRaceResponse.sessionInfo.map((session) => {
            const start: Date = new Date(this.showMyTime ? session.sessionStartTimeMyTime : session.sessionStartTimeTrack);
            const end: Date = new Date(this.showMyTime ? session.sessionEndTimeMyTime : session.sessionEndTimeTrack);
            return {
                sessionType: session.sessionType,
                sessionTime: `${moment(start).format('HH:mm')} - ${moment(end).format('HH:mm')}`,
                sessionDate: start.getDate(),
                sessionMonth: moment(start).format('MMM').toUpperCase(),
                selected: this.isCurrentSession(session),
                completed: this.now > new Date(session.sessionEndTimeMyTime),
            };
        });
    }

    private get now(): Date {
        return new Date();
    }

    public setMyTime(state: boolean) {
        this.showMyTime = state;
    }

    private isCurrentSession(session: SessionInfo): boolean {
        const nextOrCurrent: SessionInfo | undefined = this.nextRaceResponse.sessionInfo
            .filter((session) => this.now < new Date(session.sessionEndTimeMyTime))[0];
        return session === nextOrCurrent;
    }
}

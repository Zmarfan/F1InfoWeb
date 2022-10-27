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

    public get sessions(): Session[] {
        return this.nextRaceResponse.sessionInfo.map((session) => {
            const start: Date = new Date(session.sessionStartTimeMyTime);
            return {
                sessionType: session.sessionType,
                sessionTime: `${moment(start).format('HH:mm')}`,
                sessionDate: start.getDate(),
                sessionMonth: moment(start).format('MMM').toUpperCase(),
                selected: this.isNextOrCurrentSession(session),
                completed: this.now > new Date(session.sessionApproxEndTime),
            };
        });
    }

    private get now(): Date {
        return new Date();
    }

    private isNextOrCurrentSession(session: SessionInfo): boolean {
        const nextOrCurrent: SessionInfo | undefined = this.nextRaceResponse.sessionInfo
            .filter((session) => this.now < new Date(session.sessionApproxEndTime))[0];
        return session === nextOrCurrent;
    }
}

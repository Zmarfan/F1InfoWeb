import {BellNotificationClickType, BellNotificationIcon, BellNotificationResponse} from '../../../generated/server-responses';
import {IconDefinition} from '@fortawesome/free-regular-svg-icons';
import {faFaceLaughBeam, faPersonCircleQuestion} from '@fortawesome/free-solid-svg-icons';
import {Injectable} from '@angular/core';
import {RouteHolder} from '../../../app/routing/route-holder';
import {Router} from '@angular/router';
import {FriendsComponent} from '../friends/friends.component';
import {MatDialog} from '@angular/material/dialog';
import {ComponentType} from '@angular/cdk/portal';

@Injectable({
    providedIn: 'root',
})
export class BellNotificationData {
    private static BELL_NOTIFICATION_ICON_MAP: Map<BellNotificationIcon, IconDefinition> = new Map<BellNotificationIcon, IconDefinition>([
        ['HAPPY_SMILEY', faFaceLaughBeam],
        ['PERSON_CIRCLE_QUESTION', faPersonCircleQuestion],
    ]);

    private static BELL_NOTIFICATION_CLICK_ROUTING_MAP = new Map<BellNotificationClickType, string>([
        ['FEEDBACK', RouteHolder.FEEDBACK],
    ]);

    private static BELL_NOTIFICATION_CLICK_POPUP_MAP = new Map<BellNotificationClickType, ComponentType<any>>([
        ['FRIENDS', FriendsComponent],
    ]);

    public constructor(
        private mRouter: Router,
        private mDialogService: MatDialog
    ) {
    }

    public getBellNotificationIcon(response: BellNotificationResponse): IconDefinition {
        return BellNotificationData.BELL_NOTIFICATION_ICON_MAP.get(response.iconType)!;
    }

    public createBellNotificationClickCallBack(response: BellNotificationResponse): () => void {
        const clickType: BellNotificationClickType = response.clickType;
        if (BellNotificationData.BELL_NOTIFICATION_CLICK_ROUTING_MAP.has(clickType)) {
            return () => {
                this.mRouter.navigateByUrl(BellNotificationData.BELL_NOTIFICATION_CLICK_ROUTING_MAP.get(clickType)!).then();
            };
        }
        if (BellNotificationData.BELL_NOTIFICATION_CLICK_POPUP_MAP.has(clickType)) {
            return () => {
                this.mDialogService.open(BellNotificationData.BELL_NOTIFICATION_CLICK_POPUP_MAP.get(clickType)!);
            };
        }
        return () => {};
    }
}

import { Injectable } from '@angular/core';
import {HttpBackend, HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {Language} from '../../common/constants/language';
import {TranslateService} from '@ngx-translate/core';

@Injectable({
    providedIn: 'root',
})
export class WikipediaFetcherService {
    private mHttpClient: HttpClient;

    public constructor(
        private mTranslate: TranslateService,
        handler: HttpBackend
    ) {
        this.mHttpClient = new HttpClient(handler);
    }

    private get language(): Language {
        return this.mTranslate.currentLang as Language;
    }

    private static createImageCallUrl(title: string, size: number): string {
        return `https://en.wikipedia.org/w/api.php?origin=*&redirects=1&action=query&prop=pageimages&format=json&pithumbsize=${size}&titles=${title}`;
    }

    private static createSummaryUrl(title: string, language: Language): string {
        return `https://${language}.wikipedia.org/w/api.php?origin=*&format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=${title}`;
    }

    private static getPageObject(response: any): any {
        const pages = response.query.pages;
        const pageKey = Object.keys(response.query.pages)[0];
        return pages[pageKey];
    }

    public getWikipediaImageSrc(title: string, size: number): Observable<string> {
        return this.mHttpClient.get(WikipediaFetcherService.createImageCallUrl(title, size))
            .pipe(
                map((response: any) => {
                    return WikipediaFetcherService.getPageObject(response).thumbnail?.source;
                })
            );
    }

    public getWikipediaSummary(title: string): Observable<string[] | undefined> {
        return this.mHttpClient.get(WikipediaFetcherService.createSummaryUrl(title, this.language))
            .pipe(
                map((response: any) => {
                    return WikipediaFetcherService.getPageObject(response).extract as string | undefined;
                }),
                map((rawText) => {
                    return rawText?.split(/(?:\r\n|\r|\n)/g).filter((paragraph) => paragraph.length > 0);
                })
            );
    }
}

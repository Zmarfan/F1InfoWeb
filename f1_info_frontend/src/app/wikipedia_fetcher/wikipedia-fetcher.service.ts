import { Injectable } from '@angular/core';
import {HttpBackend, HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class WikipediaFetcherService {
    private mHttpClient: HttpClient;

    public constructor(
        private handler: HttpBackend
    ) {
        this.mHttpClient = new HttpClient(handler);
    }

    private static createImageCallUrl(title: string, size: number): string {
        return `https://en.wikipedia.org/w/api.php?origin=*&action=query&prop=pageimages&format=json&pithumbsize=${size}&titles=${title}`;
    }

    private static createSummaryUrl(title: string): string {
        return `https://en.wikipedia.org/w/api.php?origin=*&format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=${title}`;
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

    public getWikipediaSummary(title: string): Observable<string> {
        return this.mHttpClient.get(WikipediaFetcherService.createSummaryUrl(title))
            .pipe(
                map((response: any) => {
                    return WikipediaFetcherService.getPageObject(response).extract;
                })
            );
    }
}

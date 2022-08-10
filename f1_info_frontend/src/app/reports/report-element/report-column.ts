export class ReportColumn<T> {
    public constructor(
        public entryName: keyof T,
        public key: string
    ) {
    }
}

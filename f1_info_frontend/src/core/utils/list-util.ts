export function pushIfTrue<T, R>(list: T[], condition: R | undefined, data: T) {
    if (condition) {
        list.push(data);
    }
}

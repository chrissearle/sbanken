export const split = <T>(arr: T[], chunkSize: number): (T[] | null)[] => {
  return arr
    .map((e, i) => {
      return i % chunkSize === 0 ? arr.slice(i, i + chunkSize) : null
    })
    .filter((e) => {
      return e
    })
}

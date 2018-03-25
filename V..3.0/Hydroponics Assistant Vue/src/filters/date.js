export default (value) => {
  const date = new Date(value)
  return date.toLocaleString(['en-ASIA'], {day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit'})
}

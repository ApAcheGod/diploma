export default interface Student{
  id: String,
  first_name: String,
  last_name: String,
  patronymic: String,
  email: String | null,
  login: String,
  roles: Array<Number>,
  group: String | null,
  solutions: Array<Object> | null,
  task: Array<Object> | null
}
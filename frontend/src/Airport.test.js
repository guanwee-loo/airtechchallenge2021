import { render, screen } from '@testing-library/react';
import Airport from './Airport';

test('renders airport link', () => {
  render(<Airport />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});

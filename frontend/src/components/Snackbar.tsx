import styled from "styled-components";

type SnackbarProps = {
    message: string;
    onClick: () => void;
}

export default function Snackbar(props: Readonly<SnackbarProps>) {

    return (
        <StyledSnack>
            <SnackMessage>{props.message}</SnackMessage>
            <SnackButton onClick={props.onClick}>Alright</SnackButton>
        </StyledSnack>
    );
}

const StyledSnack = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  background-color: rgba(54, 54, 54, 0.87);
  color: var(--text-color-light);
  text-align: center;
  z-index: 5;
`
const SnackMessage = styled.span`
  margin: 40px;
`

const SnackButton = styled.button`
  background-color: transparent;
  border: var(--secondary-color) solid thin;
  border-radius: 5px;
  color: var(--text-color-light);
  margin: 0 40px 40px 40px;
  padding: 5px;
`
